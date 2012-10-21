/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import "NSDictionary+YP.h"
#import "YPAddress.h"

#pragma mark - Constants

NSString * const YPAddressStreet = @"street";
NSString * const YPAddressCity = @"city";
NSString * const YPAddressProvince = @"prov";
NSString * const YPAddressPostalCode = @"pcode";

#pragma mark - YPAddress Implementation

@implementation YPAddress

#pragma mark - Properties

@synthesize street, city, province, postalCode;

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
	self.street = [dict objectOrNilForKey:YPAddressStreet];
	self.city = [dict objectOrNilForKey:YPAddressCity];
	self.province = [dict objectOrNilForKey:YPAddressProvince];
	self.postalCode = [dict objectOrNilForKey:YPAddressPostalCode];
}

#pragma mark Memory Management

- (void)dealloc 
{
    [street release];
    [city release];
    [province release];
    [postalCode release];
    [super dealloc];
}

@end
