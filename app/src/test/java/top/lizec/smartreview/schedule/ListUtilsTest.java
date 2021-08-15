package top.lizec.smartreview.schedule;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListUtilsTest {
    private final List<SliceLocation> empty = new ArrayList<>();
    private final List<SliceLocation> divisibleList = Arrays.asList(new SliceLocation(0, 1), new SliceLocation(2, 3));
    private final List<SliceLocation> notDivisibleList = Arrays.asList(new SliceLocation(0, 3), new SliceLocation(4, 7), new SliceLocation(8, 9));
    private final List<SliceLocation> singleList = Collections.singletonList(new SliceLocation(0, 5));
    private final List<SliceLocation> singleElementList = Arrays.asList(new SliceLocation(0, 3), new SliceLocation(4, 4));


    @Test
    void genSliceZeroTest() {
        assertThrows(IllegalArgumentException.class,
                () -> ListUtils.genSlice(0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> ListUtils.genSlice(2, 0));
    }

    @Test
    public void genSliceEmptyTest() {
        assertEquals(empty, ListUtils.genSlice(0, 2));
    }

    @Test
    public void genSliceBaseTest() {
        assertEquals(divisibleList, ListUtils.genSlice(4, 2));
        assertEquals(notDivisibleList, ListUtils.genSlice(10, 4));

        assertEquals(singleList, ListUtils.genSlice(6, 6));
        assertEquals(singleList, ListUtils.genSlice(6, 10));

        assertEquals(singleElementList, ListUtils.genSlice(5, 4));
    }


}