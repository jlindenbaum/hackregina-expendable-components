/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import "NSDictionary+YP.h"
#import "YPListing.h"

#pragma mark - Constants

NSString * const kYPListingIdentifier = @"id";
NSString * const kYPListingName = @"name";
NSString * const kYPListingAddress = @"address";
NSString * const kYPListingPhones = @"phones";
NSString * const kYPListingLocation = @"geoCode";
NSString * const kYPListingLocationLatitude = @"latitude";
NSString * const kYPListingLocationLongitude = @"longitude";
NSString * const kYPListingLogos = @"logos";
NSString * const kYPListingLogosEn = @"EN";
NSString * const kYPListingLogosFr = @"FR";
NSString * const kYPListingMerchantUrl = @"merchantUrl";

#pragma mark - YPListing Implementation

@implementation YPListing

#pragma mark - Properties

@synthesize identifier, name, address, phones, location, enLogo, frLogo, merchantUrl;

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
	// update properties that are strings
	self.identifier = [dict objectOrNilForKey:kYPListingIdentifier];
	self.name = [dict objectOrNilForKey:kYPListingName];
	self.merchantUrl = [dict objectOrNilForKey:kYPListingMerchantUrl];
	
	// update the latitude and longitude of this listing
	NSDictionary *locationDict = [dict objectOrNilForKey:kYPListingLocation];
	if (locationDict != nil) {
		location.latitude = [[locationDict objectOrNilForKey:kYPListingLocationLatitude] doubleValue];
		location.longitude = [[locationDict objectOrNilForKey:kYPListingLocationLongitude] doubleValue];
	}
	
	// create or update a new address for this listing
	NSDictionary *addressDict = [dict objectOrNilForKey:kYPListingAddress];
	if (addressDict != nil) {
		if (!address) {
			self.address = [[YPAddress alloc] init];
		}
		[self.address updateWithDictionary:addressDict];
	}
	
	// add any phone numbers for this listing, if available
	NSArray *phoneNumbers = [dict objectOrNilForKey:kYPListingPhones];
	if (phoneNumbers.count > 0) {
		NSMutableArray *mutablePhoneNumbers = [NSMutableArray arrayWithCapacity:phoneNumbers.count];
		for (NSDictionary *phoneDict in phoneNumbers) {
			YPPhone *phone = [[YPPhone alloc] initWithDictionary:phoneDict];
			[mutablePhoneNumbers addObject:phone];
			[phone release];
		}
		
		self.phones = [[mutablePhoneNumbers copy] autorelease];
	}

	// update the english and/or french logo for this listing
	NSDictionary *logoDict = [dict objectOrNilForKey:kYPListingLogos];
	if (logoDict != nil && logoDict.count > 0) {
		NSString *enLogoLoc = [logoDict objectOrNilForKey:kYPListingLogosEn];
		if (enLogoLoc) {
			self.enLogo = enLogoLoc;
		}
		
		NSString *frLogoLoc = [logoDict objectOrNilForKey:kYPListingLogosFr];
		if (frLogoLoc) {
			self.frLogo = frLogoLoc;
		}
	}	
}

#pragma Memory Management

- (void)dealloc 
{
	[identifier release];
	[name release];
	[address release];
	[phones release];
	[enLogo release];
	[frLogo release];
	[merchantUrl release];
	[super dealloc];
}

@end
