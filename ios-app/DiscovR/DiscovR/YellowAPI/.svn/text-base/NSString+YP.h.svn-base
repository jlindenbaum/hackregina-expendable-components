/*
 Copyright � 2011, Yellow Pages Group Co.  All rights reserved.
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 
 1)	Redistributions of source code must retain a complete copy of this notice, including the copyright notice, this list of conditions and the following disclaimer; and
 2)	Neither the name of the Yellow Pages Group Co., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT OWNER AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#import <Foundation/Foundation.h>

/**
 @brief A category which allows a string to be formatted properly for the YellowAPI
 */
@interface NSString (YP)

/**
 @brief Returns an NSString which follows the YellowAPI encoding "guidelines":
 
 <ul>
	<li>All accents much be removed and converted to non-accented characters.</li>
	<li>All non-alpha-numeric characters must be replaced by a dash.</li>
	<li>Multiple dashes must be replaced by a single dash</li>
 </ul>
 
 @return A string which is well-formed to the YellowAPI guidelines listed above.
 */
- (NSString *)stringWithYPEncoding;

@end
