/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import "NSString+YP.h"

@implementation NSString (YP)

#pragma mark - String Encoding Helper Methods

- (NSString *)ypRemoveUnicode
{
	// encode accented characters into their non-accented form
	return [[[NSString alloc] initWithData:[self dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES] encoding:NSASCIIStringEncoding] autorelease];
}

- (NSString *)ypCleanString
{
	// only allow alphanumeric characters
	NSCharacterSet *invertedSet = [[NSCharacterSet alphanumericCharacterSet] invertedSet];
	
	// split the string into an array based on non-alphanumeric characters
	NSArray *components = [[self stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]] componentsSeparatedByCharactersInSet:invertedSet];
	
	NSMutableString *mutable = [NSMutableString string];
	if ([components count] > 0) {
		[mutable appendString:[components objectAtIndex:0]];
		for (int i = 1; i < components.count; ++i) {
			NSString *component = [components objectAtIndex:i];
			if (component.length > 0) {
				[mutable appendFormat:@"-%@", component];
			}
		}
	}
	
	return [NSString stringWithString:mutable];
}

- (NSString *)stringWithYPEncoding
{
	return [[self ypRemoveUnicode] ypCleanString];
}

@end