/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "YPListingSummary.h"

extern NSString * const YPYellowAPIErrorDomain;

typedef void (^YPFindBusinessCallback)(YPListingSummary *listings, NSError *error);
typedef void (^YPBusinessDetailsCallback)(YPListing *listing, NSError *error);

/**
 @brief The YPYellowAPI class
 
 Allows an application to make specific calls to the YellowAPI. Currently supports:
 <ul>
	<li>FindBusiness: Returns local Canadian businesses that are most relevant,</li>
	<li>GetBusinessDetails: Returns the details of the queried business</li>
 </ul>
 */
@interface YPYellowAPI : NSObject {
@private
	NSString *url;
	NSString *uid;
    NSString *apiKey;
}

/**
 @brief The unique identifier of the user's iOS device
 */
@property (nonatomic, readonly, copy) NSString *uid;

/**
 @brief The API key used by this application
 */
@property (nonatomic, readonly, copy) NSString *apiKey;

/**
 @brief Access the YellowAPI using a particular API key
 
 Uses the production API server by default
 */
- (id)initWithAPIKey:(NSString *)apiKey;

/**
 @brief Initialize the YellowAPI using a particular API key and server
 */
- (id)initWithAPIKey:(NSString *)apiKey sandbox:(BOOL)sandbox;

/**
 @brief Get a YPListingSummary object by accessing the YellowAPI "FindBusiness" endpoint.
 
 Example usage: 
 
 [yellowApiInstance findBusiness:aBusinessString atCoordinate:coord callback:^(YPListingSummary *summary, NSError *error) {
 if (!error) {
 // handle returned YPListingSummary
 // display notification to user
 }
 else {
 // handle error(s)
 }
 }];
 */
- (void)findBusiness:(NSString *)what atCoordinate:(CLLocationCoordinate2D)coord callback:(YPFindBusinessCallback)callback;

/**
 @brief Get a YPListingSummary object by accessing the YellowAPI "FindBusiness" endpoint.
 
 Example usage: 
 
 [yellowApiInstance findBusiness:aBusinessString locatedIn:aLocation callback:^(YPListingSummary *summary, NSError *error) {
	if (!error) {
		// handle returned YPListingSummary
		// display notification to user
	}
	else {
		// handle error(s)
	}
 }];
 */
- (void)findBusiness:(NSString *)what locatedIn:(NSString *)where callback:(YPFindBusinessCallback)callback;

/**
 @brief Get a YPListing object by accessing the YellowAPI "GetBusinessDetails" endpoint.
 
 Example usage: 
 
 [yellowApiInstance getBusinessDetails:aUniqueId prov:@"Canada" name:businessName callback:^(YPListing *listing, NSError *error) {
	if (!error) {
		// handle returned YPListing
		// display notification to user
	}
	else {
		// handle error(s)
	}
 }];
 */
- (void)getBusinessDetails:(NSString *)identifier prov:(NSString *)prov name:(NSString *)name city:(NSString *)city callback:(YPBusinessDetailsCallback)callback;

@end