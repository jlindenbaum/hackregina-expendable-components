/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import <Foundation/Foundation.h>
#import "YPListing.h"

/**
 @brief The YPListingSummary class
 
 A class representing the search results of querying the YellowAPI's FindBusiness
 endpoint. Contains a summary of the query as well as an array of YPListing objects
 */
@interface YPListingSummary : NSObject {
@private 
	NSString  *what;
	NSString  *where;
	NSString  *prov;
	NSInteger firstListing;
	NSInteger lastListing;
	NSInteger totalListings;
	NSInteger pageCount;
	NSInteger currentPage;
	NSInteger listingsPerPage;
	NSArray   *listings;
	YPLocation location;
}

/**
 @brief What the user was searching for
 */
@property (nonatomic, readwrite,   copy) NSString *what;

/**
 @brief Where the user was searching for it
 */
@property (nonatomic, readwrite,   copy) NSString *where;

/**
 @brief The province that the query returned data for
 */
@property (nonatomic, readwrite,   copy) NSString *prov;

/**
 @brief The index of the first listing in the query
 */
@property (nonatomic, readwrite, assign) NSInteger firstListing;

/**
 @brief The index of the last listing in the query
 */
@property (nonatomic, readwrite, assign) NSInteger lastListing;

/**
 @brief The total number of listings for this query
 */
@property (nonatomic, readwrite, assign) NSInteger totalListings;

/**
 @brief The total number of pages for this query
 */
@property (nonatomic, readwrite, assign) NSInteger pageCount;

/**
 @brief The current page of this query
 */
@property (nonatomic, readwrite, assign) NSInteger currentPage;

/**
 @brief The total number of listings in this particular page
 */
@property (nonatomic, readwrite, assign) NSInteger listingsPerPage;

/**
 @brief The entire set of YPListings in an NSArray
 */
@property (nonatomic, readwrite, retain) NSArray *listings;

/**
 @brief The lat/long location of this query on a map
 */
@property (nonatomic, readwrite, assign) YPLocation location;

/**
 @brief Create a new YPListingSummary using an NSDictionary
 */
- (id)initWithDictionary:(NSDictionary *)dict;

/**
 @brief Update the current YPListingSummary using an NSDictionary
 */
- (void)updateWithDictionary:(NSDictionary *)dict;

@end
