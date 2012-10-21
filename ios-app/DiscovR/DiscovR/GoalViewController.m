//
//  GoalViewController.m
//  DiscovR
//
//  Created by Landon Rohatensky on 12-10-20.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "GoalViewController.h"

@interface GoalViewController ()

@end

@implementation GoalViewController
@synthesize goalMapView, goalLabel, checkInButton, ypTableView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	goalMapView.userInteractionEnabled = FALSE;
	[self getCurrentGoal];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

-(void)getCurrentGoal
{
	NSHTTPURLResponse * response;
	NSError * error;
	
	NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
	[request setURL:[NSURL URLWithString:@"http://discovr.azure-mobile.net/tables/CurrentGoal"]];
	[request setHTTPMethod:@"GET"];
	[request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
	[request setValue:@"EKcZFrVKadjfiDEsGCxoAyvFIYhCPn15" forHTTPHeaderField:@"X-ZUMO-APPLICATION"];
	
	NSData *currentGoal = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];	
	NSError *e = nil;
	
	NSArray *jsonArray = [NSJSONSerialization JSONObjectWithData:currentGoal options: NSJSONReadingMutableContainers error: &e];
	
	for(NSDictionary *item in jsonArray) {
		NSLog(@"Item: %@", item);
	}
	
	int points = [[[jsonArray valueForKey:@"CurrentPointsAward"] objectAtIndex:0] intValue];
	float latitude = [[[jsonArray valueForKey:@"Lat"] objectAtIndex:0] floatValue];
	float longitude = [[[jsonArray valueForKey:@"Long"] objectAtIndex:0] floatValue];
	NSMutableString *goalName = [[NSMutableString alloc] initWithFormat:@"%@", [[jsonArray valueForKey:@"Title"] objectAtIndex:0]];
	NSString *goalAddress = [[NSString alloc] initWithFormat:@"%@", [[jsonArray valueForKey:@"Address"] objectAtIndex:0]];
	
	goalId = [[[jsonArray valueForKey:@"id"] objectAtIndex:0] intValue];
	
	goalLocation = CLLocationCoordinate2DMake(latitude, longitude);
	
	[checkInButton setTitle:[NSString stringWithFormat:@"Check In For %d Points", points ] forState:UIControlStateNormal];	
	
	[goalName setString:[goalName stringByReplacingOccurrencesOfString:@" - " withString:@"\n"]];
	[goalLabel setText:goalName];
	
	MKCoordinateRegion region = { {latitude, longitude}, { 0.005, 0.005 } };
	[goalMapView setRegion:region animated:NO]; 
	
	AddressAnnotation *daAnnotation = [[AddressAnnotation alloc] initWithTitle:goalName sub:goalAddress p:region.center];
	[goalMapView selectAnnotation:daAnnotation animated:YES];
}

-(IBAction)checkIn:(id)sender
{
	NSLog(@"check in");
	
	UIAlertView *checkInLoadingDisplay = [[UIAlertView alloc] initWithTitle:@"Checking In" message:@"" delegate:self cancelButtonTitle:nil otherButtonTitles:nil, nil];  
	
	UIActivityIndicatorView *loading = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
	[loading startAnimating];
	loading.frame=CGRectMake(134, 60, 16, 16);
	[checkInLoadingDisplay addSubview:loading];
	[checkInLoadingDisplay show];
	
	[self yellowBooksCall];
	
	NSHTTPURLResponse * response;
	NSError * error;
	
    NSString *post = [NSString stringWithFormat:@"{\"ObjectId\":\"0\",\"UserId\":\"landonroha@gmail.com\"}", goalId];
	NSData *postData = [post dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
	
	NSString *postLength = [NSString stringWithFormat:@"%d", [postData length]];
	
	
	NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
	[request setURL:[NSURL URLWithString:@"http://discovr.azure-mobile.net/tables/CheckIn"]];
	[request setHTTPMethod:@"POST"];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	[request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
	[request setValue:@"EKcZFrVKadjfiDEsGCxoAyvFIYhCPn15" forHTTPHeaderField:@"X-ZUMO-APPLICATION"];
	[request setHTTPBody:postData];
	
	NSData *currentGoal = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];	
	NSError *e = nil;
	NSArray *jsonArray = [NSJSONSerialization JSONObjectWithData:currentGoal options: NSJSONReadingMutableContainers error: &e];
	
	[checkInButton setTitle:@"Accomplished!" forState:UIControlStateNormal];
	checkInButton.userInteractionEnabled = FALSE;
	
	[checkInLoadingDisplay dismissWithClickedButtonIndex:0 animated:YES];
	
	checkInLoadingDisplay = [[UIAlertView alloc] initWithTitle:@"Dora The Explorer!" message:@"You've checked in. That's awesome. Check out what's around" delegate:self cancelButtonTitle:nil otherButtonTitles:@"OK", nil];  
	[checkInLoadingDisplay show];
}

-(void)yellowBooksCall
{
	YPYellowAPI *yellowAPI = [[YPYellowAPI alloc] initWithAPIKey:@"mzpcne2fjv8qszj5v627ra9b" sandbox:YES];
	listings = [[NSMutableArray alloc] initWithCapacity:2];
	[yellowAPI findBusiness:@"restaurants" atCoordinate:goalLocation callback:^(YPListingSummary *summary, NSError *error) {
		if (!error) {
			[listings addObject:[summary.listings valueForKey:@"name"]];
			[listings addObject:[summary.listings valueForKey:@"address"]];
			ypTableView.hidden = FALSE;
			[ypTableView reloadData];
		}
		else {
			NSLog(@"%@", error);
		}
	}];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	if(listings)
		return [[listings objectAtIndex:0] count];
	else
		return 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	NSString *CellIdentifier = @"Cell";
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	if (cell == nil) {
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
	}
	
	if(listings){
		cell.textLabel.text = [[listings objectAtIndex:0] objectAtIndex:indexPath.row];
		NSString *addressString = [[[listings objectAtIndex:1] objectAtIndex:indexPath.row] valueForKey:@"street"];
		NSString *cityString = [[[listings objectAtIndex:1] objectAtIndex:indexPath.row] valueForKey:@"city"];
		cell.detailTextLabel.text = [NSString stringWithFormat:@"%@, %@", addressString, cityString];
	}
	return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
	NSString *cellText = cell.textLabel.text;
    NSString *cellDetailText = cell.detailTextLabel.text;
	
	//Build the string to Query Google Maps.
	NSMutableString *urlString = [NSMutableString stringWithFormat:@"http://maps.google.com/maps/geo?q=%@, %@", cellText, cellDetailText];
	
	//Replace Spaces with a '+' character.
	[urlString setString:[urlString stringByReplacingOccurrencesOfString:@" " withString:@"+"]];
	
	//Create NSURL string from a formate URL string.
	NSURL *url = [NSURL URLWithString:urlString];
	
	[[UIApplication sharedApplication] openURL:url];
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
