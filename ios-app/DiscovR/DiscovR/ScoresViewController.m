//
//  SecondViewController.m
//  DiscovR
//
//  Created by Landon Rohatensky on 12-10-20.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ScoresViewController.h"

@interface ScoresViewController ()

@end

@implementation ScoresViewController
@synthesize scoreTableView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	[self getLeaderboard];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

-(void)getLeaderboard
{
	NSLog(@"leaderboards");
	NSHTTPURLResponse * response;
	NSError * error;
	
	NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
	[request setURL:[NSURL URLWithString:@"http://discovr.azure-mobile.net/tables/Leaderboard"]];
	[request setHTTPMethod:@"GET"];
	[request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
	[request setValue:@"EKcZFrVKadjfiDEsGCxoAyvFIYhCPn15" forHTTPHeaderField:@"X-ZUMO-APPLICATION"];
	
	NSData *currentGoal = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];	
	NSError *e = nil;
	scores = [[NSArray alloc] initWithArray:[NSJSONSerialization JSONObjectWithData:currentGoal options: NSJSONReadingMutableLeaves error: &e]];
	for(NSDictionary *item in scores)
	{
		NSLog(@"item %@", item);
	}
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	if(scores)
		return [scores count];
	else
		return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	NSString *CellIdentifier = @"Cell";
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	if (cell == nil) {
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
	}
	
	UILabel *emailLabel = [[UILabel alloc] initWithFrame:CGRectMake(5, 0, 205, 30)];
	[emailLabel setTextAlignment:UITextAlignmentLeft];
	[emailLabel setBackgroundColor:[UIColor clearColor]];
	[emailLabel setFont:[UIFont fontWithName:@"Helvetica-Bold" size:16]];
	[cell addSubview:emailLabel];
	
	UILabel *scoreLabel = [[UILabel alloc] initWithFrame:CGRectMake(200, 0, 115, 30)];
	[scoreLabel setTextAlignment:UITextAlignmentRight];
	[scoreLabel setBackgroundColor:[UIColor clearColor]];
	[cell addSubview:scoreLabel];
	
	NSString *username = [[scores objectAtIndex:indexPath.row] valueForKey:@"UserId"];
	int userCheckins, userPoints;
	if([[scores objectAtIndex:indexPath.row] valueForKey:@"Points"] != [NSNull null])
		userPoints = [[[scores objectAtIndex:indexPath.row] valueForKey:@"Points"] intValue];
	else
		userPoints = 0;
	if([[scores objectAtIndex:indexPath.row] valueForKey:@"TotalCheckins"] != [NSNull null])
		userCheckins = [[[scores objectAtIndex:indexPath.row] valueForKey:@"TotalCheckins"] intValue];
	else
		userCheckins = 0;
	
	cell.textLabel.text = @" ";
	[emailLabel setText:username];
	[scoreLabel setText:[NSString stringWithFormat:@"Points: %d", userPoints]];
	
	cell.detailTextLabel.text = [NSString stringWithFormat:@"Total Check-ins %d", userCheckins];
	return cell;
}





- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
	    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
	} else {
	    return YES;
	}
}

@end
