/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import <Foundation/Foundation.h>

/**
 @brief The YPAddress class
 
 Represents the street, city, province and postal code of a merchant.
 */
@interface YPAddress : NSObject {
@private
    NSString *street;
    NSString *city;
    NSString *province;
    NSString *postalCode;
}

/**
 @brief The street of a merchant
 */
@property (nonatomic, readwrite, copy) NSString *street;

/**
 @brief The city of a merchant
 */
@property (nonatomic, readwrite, copy) NSString *city;

/**
 @brief The province of a merchant
 */
@property (nonatomic, readwrite, copy) NSString *province;

/**
 @brief The postal code of a merchant
 */
@property (nonatomic, readwrite, copy) NSString *postalCode;

/**
 @brief Create a YPAddress object using an NSDictionary
 */
- (id)initWithDictionary:(NSDictionary *)dict;

/**
 @brief Update a YPAddress object using an NSDictionary
 */
- (void)updateWithDictionary:(NSDictionary *)dict;

@end
