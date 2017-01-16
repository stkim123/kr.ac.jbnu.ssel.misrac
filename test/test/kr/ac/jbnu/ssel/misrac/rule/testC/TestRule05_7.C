struct air_speed
{
    uint16_t speed; /* knots */
} * x;
struct gnd_speed
{
    uint16_t speed; /* mph                                         */
                    /* Not Compliant - speed is in different units */
} * y;
x->speed = y->speed;
