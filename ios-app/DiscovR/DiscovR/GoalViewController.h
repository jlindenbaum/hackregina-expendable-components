//
//  GoalViewController.h
//  DiscovR
//
//  Created by Landon Rohatensky on 12-10-20.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "AddressAnnotation.h"
#import "YPYellowAPI.h"

@interface GoalViewController : UIViewController
{
	MKMapView	*goalMapView;
	UILabel		*goalLabel;
	UIButton	*checkInButton;
	UITableView *ypTableView;
	NSMutableArray *listings;
	CLLocationCoordinate2D goalLocation;
	int goalId;
	
};
-(void)getCurrentGoal;
-(void)showAlert:(id)sender;
-(void)yellowBooksCall;

@property (strong, nonatomic) IBOutlet MKMapView *goalMapView;
@property (strong, nonatomic) IBOutlet UITableView *ypTableView;
@property(strong, nonatomic) IBOutlet UILabel *goalLabel;
@property(nonatomic, retain)IBOutlet UIButton *checkInButton;
-(IBAction)checkIn:(id)sender;
@end
