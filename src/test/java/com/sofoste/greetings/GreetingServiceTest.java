package com.sofoste.greetings;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;
class GreetingServiceTest {
 @Test void greetsInThreeLanguages(){ assertEquals("Good morning, Sam!",GreetingsApp.greet(" Sam ",Locale.ENGLISH,LocalTime.of(8,0))); assertEquals("Bon après-midi, Léa !",GreetingsApp.greet("Léa",Locale.FRENCH,LocalTime.of(14,0))); assertEquals("Guten Abend, Ada!",GreetingsApp.greet("Ada",Locale.GERMAN,LocalTime.of(20,0))); }
 @Test void guidesEmptyInput(){ assertTrue(GreetingsApp.greet("  ",Locale.ENGLISH,LocalTime.NOON).startsWith("Enter")); }
}
