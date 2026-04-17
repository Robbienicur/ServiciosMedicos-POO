package Emergencias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResponsableItemTest {

    @Test
    void getIdReturnsCorrectValue() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertEquals(1, item.getId());
    }

    @Test
    void getNombreReturnsCorrectValue() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertEquals("Dr. Garcia", item.getNombre());
    }

    @Test
    void toStringReturnsNombre() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertEquals("Dr. Garcia", item.toString());
    }

    @Test
    void equalsWithSameObject() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertTrue(item.equals(item));
    }

    @Test
    void equalsWithEqualValues() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(1, "Dr. Garcia");
        assertTrue(item1.equals(item2));
    }

    @Test
    void equalsSymmetric() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(1, "Dr. Garcia");
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));
    }

    @Test
    void notEqualWhenDifferentId() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(2, "Dr. Garcia");
        assertFalse(item1.equals(item2));
    }

    @Test
    void notEqualWhenDifferentNombre() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(1, "Dr. Lopez");
        assertFalse(item1.equals(item2));
    }

    @Test
    void notEqualWhenBothDifferent() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(2, "Dr. Lopez");
        assertFalse(item1.equals(item2));
    }

    @Test
    void notEqualToNull() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertFalse(item.equals(null));
    }

    @Test
    void notEqualToDifferentType() {
        ResponsableItem item = new ResponsableItem(1, "Dr. Garcia");
        assertFalse(item.equals("Dr. Garcia"));
    }

    @Test
    void hashCodeConsistentForEqualObjects() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(1, "Dr. Garcia");
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void hashCodeMatchesExpectedFormula() {
        ResponsableItem item = new ResponsableItem(5, "Dr. Garcia");
        int expected = 5 + "Dr. Garcia".hashCode();
        assertEquals(expected, item.hashCode());
    }

    @Test
    void hashCodeDiffersForDifferentObjects() {
        ResponsableItem item1 = new ResponsableItem(1, "Dr. Garcia");
        ResponsableItem item2 = new ResponsableItem(2, "Dr. Lopez");
        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void hashCodeConsistentOnMultipleCalls() {
        ResponsableItem item = new ResponsableItem(10, "Enfermera Ruiz");
        int first = item.hashCode();
        int second = item.hashCode();
        assertEquals(first, second);
    }
}
