/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import <Foundation/Foundation.h>

/**
 @brief The YPPhone class
 
 A class which represents a phone number in the YellowAPI.
 */
@interface YPPhone : NSObject {
@private
	NSString *type;
	NSString *npa;
	NSString *nxx;
	NSString *num;
	NSString *displayNumber;
}

/**
 @brief The type of phone number this object is, ie: Primary
 */
@property (nonatomic, readwrite, copy) NSString *type;

/**
 @brief The 3-digit area code of this phone number, ie: 613
 */
@property (nonatomic, readwrite, copy) NSString *npa;

/**
 @brief The 3-digit station code of this phone number, ie: 555
 */
@property (nonatomic, readwrite, copy) NSString *nxx;

/**
 @brief The 4-digit number of this phone number, ie: 5555
 */
@property (nonatomic, readwrite, copy) NSString *num;

/**
 @brief The full 10-digit, human readable display of this number.
 */
@property (nonatomic, readwrite, copy) NSString *displayNumber;

/**
 @brief Initialize this object using an NSDictionary
 */
- (id)initWithDictionary:(NSDictionary *)dict;

/**
 @brief Update the properties of this object using an NSDictionary
 */
- (void)updateWithDictionary:(NSDictionary *)dict;

@end
