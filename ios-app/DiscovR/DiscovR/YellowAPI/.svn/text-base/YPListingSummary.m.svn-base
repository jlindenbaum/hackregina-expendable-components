/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import "NSDictionary+YP.h"
#import "YPListingSummary.h"

#pragma mark - Constants

NSString * const YPListingSummaryKey = @"summary";
NSString * const YPListingSummaryListings = @"listings";
NSString * const YPListingSummaryWhat = @"what";
NSString * const YPListingSummaryWhere = @"where";
NSString * const YPListingSummaryProvince = @"Prov";
NSString * const YPListingSummaryLatitude = @"latitude";
NSString * const YPListingSummaryLongitude = @"longitude";
NSString * const YPListingSummaryFirstListing = @"firstListing";
NSString * const YPListingSummaryLastListing = @"lastListing";
NSString * const YPListingSummaryTotalListings = @"totalListings";
NSString * const YPListingSummaryPageCount = @"pageCount";
NSString * const YPListingSummaryCurrentPage = @"currentPage";
NSString * const YPListingSummaryListingsPerPage = @"listingsPerPage";

#pragma mark - YPListingSummary Implementation

@implementation YPListingSummary

#pragma mark - Properties

@synthesize what;
@synthesize where;
@synthesize prov;
@synthesize firstListing;
@synthesize lastListing;
@synthesize totalListings;
@synthesize pageCount;
@synthesize currentPage;
@synthesize listingsPerPage;
@synthesize listings;
@synthesize location;

#pragma mark - Initialization

- (id)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    if (self) {
		[self updateWithDictionary:dict];
    }
    
    return self;
}

#pragma mark - Update Logic

- (void)updateWithDictionary:(NSDictionary *)dict
{
	NSDictionary *summaryDict = [dict objectOrNilForKey:YPListingSummaryKey];
	
	// if there is a summary object found in the json, populate it
	if (summaryDict != nil) {
		self.what = [dict objectOrNilForKey:YPListingSummaryWhat];
		self.where = [dict objectOrNilForKey:YPListingSummaryWhere];
		self.prov = [dict objectOrNilForKey:YPListingSummaryProvince];
		self.firstListing = [[dict objectOrNilForKey:YPListingSummaryFirstListing] intValue];
		self.lastListing = [[dict objectOrNilForKey:YPListingSummaryLastListing] intValue];
		self.totalListings = [[dict objectOrNilForKey:YPListingSummaryTotalListings] intValue];
		self.pageCount = [[dict objectOrNilForKey:YPListingSummaryPageCount] intValue];
		self.currentPage = [[dict objectOrNilForKey:YPListingSummaryCurrentPage] intValue];
		self.listingsPerPage = [[dict objectOrNilForKey:YPListingSummaryListingsPerPage] intValue];
	
		// will these return 0.0 if the string is ""?
		double _lat = [[dict objectOrNilForKey:YPListingSummaryLatitude] doubleValue];
		double _long = [[dict objectOrNilForKey:YPListingSummaryLongitude] doubleValue];
	
		if (_lat && _long) {
			location.latitude = _lat;
			location.longitude = _long;
		}
		
		NSArray *listingsArray = [dict objectOrNilForKey:YPListingSummaryListings];
		
		if (listingsArray) {
			// for each dictionary, create a YPListing and add it to the new array
			NSMutableArray *mutableListings = [[NSMutableArray alloc] initWithCapacity:[listingsArray count]];
			
			for (NSDictionary *dictionary in listingsArray) {
				[mutableListings addObject:[[[YPListing alloc] initWithDictionary:dictionary] autorelease]];
			}
			
			self.listings = [[mutableListings copy] autorelease];
			[mutableListings release];
		}
	}
}

#pragma mark - Memory Management

- (void)dealloc
{
	[what release];
	[where release];
	[prov release];
	[listings release];
	[super dealloc];
}

@end
