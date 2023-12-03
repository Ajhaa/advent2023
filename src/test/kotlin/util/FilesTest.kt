package util

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FilesTest {

    @Test
    fun `getResource returns a file`() {
        val resource = getResource("input1.txt")
        assertNotNull(resource)
        assertTrue { resource.isNotBlank() }
    }
}