package fred.poker;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LookupTest {

    private Lookup lookup;

    @BeforeEach
    public void setUp() {
        lookup = new Lookup();
        lookup.lookUpTable();
    }

    @Test
    public void testFlushLookupSize() {
        assertEquals(1287, lookup.getFlushLookup().size());
    }

    @Test
    public void testUnsuitedLookupSize() {
        assertEquals(6175, lookup.getUnsuitedLookup().size());
    }

    @Test
    public void testSingleBit() {
        assertEquals(2, lookup.primeProductFromRankBits(0b0000000000001));
        assertEquals(3, lookup.primeProductFromRankBits(0b0000000000010));
        assertEquals(5, lookup.primeProductFromRankBits(0b0000000000100));
    }

    @Test
    public void testMultipleBits() {
        assertEquals(2 * 3 * 5, lookup.primeProductFromRankBits(0b0000000000111));
        assertEquals(2 * 3 * 5 * 7, lookup.primeProductFromRankBits(0b0000000001111));
    }

    @Test
    public void testNoBitsSet() {
        assertEquals(1, lookup.primeProductFromRankBits(0b0000000000000));
    }

    @Test
    public void testAllBitsSet() {
        // Forcer l'arithmétique longue sinon overflow
        long expectedProduct = 2L * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23 * 29 * 31 * 37 * 41;
        assertEquals(expectedProduct, lookup.primeProductFromRankBits(0b1111111111111));
    }

}
