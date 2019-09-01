package io.mincongh.library.mockito.init;

import io.mincongh.library.Book;
import io.mincongh.library.BookReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Mock object {@code mockedBook} is created by {@link MockitoJUnitRunner}.
 *
 * @author Mincong Huang
 */
@RunWith(MockitoJUnitRunner.class)
public class BookReaderAnnotationWithRunnerTest {

  private BookReader reader;

  @Mock Book mockedBook;

  @Before
  public void setUp() {
    reader = new BookReader(mockedBook);
  }

  @Test
  public void testPrintContent() {
    mockedBook.printContent();
    Mockito.verify(mockedBook).printContent();
  }

  @Test
  public void testGetContent() {
    Mockito.when(mockedBook.getContent()).thenReturn("Mockito");
    assertEquals("Mockito", reader.getContent());
  }
}