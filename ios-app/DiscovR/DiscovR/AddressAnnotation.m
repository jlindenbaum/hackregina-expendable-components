#import "AddressAnnotation.h"

@implementation AddressAnnotation
@synthesize coordinate, title, subtitle;

- (id) initWithTitle:(NSString *)_title sub:(NSString*)_subtitle p:(CLLocationCoordinate2D)_point
{
    coordinate = _point;
    title = _title;
	subtitle = _subtitle;
    return self;
}

@end