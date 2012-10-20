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
@synthesize goalMapView, goalLabel, checkInButton;

- (void)viewDidLoad
{
    [super viewDidLoad];
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
	NSString *goalName = [[NSString alloc] initWithFormat:@"%@", [[jsonArray valueForKey:@"Title"] objectAtIndex:0]];
	NSString *goalAddress = [[NSString alloc] initWithFormat:@"%@", [[jsonArray valueForKey:@"Address"] objectAtIndex:0]];

	goalId = [[[jsonArray valueForKey:@"id"] objectAtIndex:0] intValue];

	[checkInButton setTitle:[NSString stringWithFormat:@"Check In For %d Points", points ] forState:UIControlStateNormal];	
	[goalLabel setText:goalName];

	MKCoordinateRegion region = { {latitude, longitude}, { 0.005, 0.005 } };
	[goalMapView setRegion:region animated:NO]; 
	
	AddressAnnotation *daAnnotation = [[AddressAnnotation alloc] initWithTitle:goalName sub:goalAddress p:region.center];
	[goalMapView selectAnnotation:daAnnotation animated:YES];
}

-(IBAction)checkIn:(id)sender
{
	NSLog(@"check in");
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
	
	NSLog(@"Object Id = %@", [jsonArray valueForKey:@"ObjectId"]);
	NSLog(@"User Id = %@", [jsonArray valueForKey:@"UserId"]);
	NSLog(@"id = %@", [jsonArray valueForKey:@"id"]);
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
