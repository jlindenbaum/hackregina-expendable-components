//
//  SecondViewController.h
//  DiscovR
//
//  Created by Landon Rohatensky on 12-10-20.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ScoresViewController : UIViewController
{
	NSArray	*scores;
}

-(void)getLeaderboard;

@property (strong, nonatomic) IBOutlet UITableView *scoreTableView;
@end
