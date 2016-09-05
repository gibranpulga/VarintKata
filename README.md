#This is my solution to Varint kata by [Eric Le Merdy](http://eric.lemerdy.name/)

Problem description is at the end.

# Notes

It is working but there are some things I would improve:
  - VarintKata class is doing too many things. Because of this the tests are too broad;
  - Because of above, there are some private methods on VarintKata that weren´t tested. They should be extracted into a new class and methods made public so testing could be easier;
  - I can see at least 3 or 4 different classes could be created of it;
  - Because of this, it was a little bit harder to arrive at the final logic. As TDD states, minimal things should be tested, and with my approach I went for the test of the final result only. This I would have made different;
  - The logic for transforming int to varint is different than the one used to transform varint to int. Maybe it could be rearrange so there could be some code reuse.

==========================================================================================

## Problem description ##

Your mission is to convert any int in its varint binary representation.

    int -> varint

As an extra exercice, do the opposite: try to convert varints binary representation to int. What is the amount of duplicated code?

## Documentation ##

Ref. [https://developers.google.com/protocol-buffers/docs/encoding](https://developers.google.com/protocol-buffers/docs/encoding)

### Base 128 Varints ###

To understand your simple protocol buffer encoding, you first need to understand varints. Varints are a method of serializing integers using one or more bytes. Smaller numbers take a smaller number of bytes.

Each byte in a varint, except the last byte, has the most significant bit (*msb*) set – this indicates that there are further bytes to come. The lower 7 bits of each byte are used to store the two's complement representation of the number in groups of 7 bits, least significant group first.

So, for example, here is the number **1** – it's a single byte, so the msb is not set:

    0000 0001

And here is **300** – this is a bit more complicated:

       1010 1100 0000 0010

How do you figure out that this is 300? First you drop the msb from each byte, as this is just there to tell us whether we've reached the end of the number (as you can see, it's set in the first byte as there is more than one byte in the varint):

       1010 1100 0000 0010
    ->  010 1100  000 0010

You reverse the two groups of 7 bits because, as you remember, varints store numbers with the least significant group first.

        010 1100  000 0010
    ->  000 0010  010 1100

Then you concatenate them to get your final value:

	    000 0010 ++ 010 1100
	->  100101100
	->  256 + 32 + 8 + 4 = 300
