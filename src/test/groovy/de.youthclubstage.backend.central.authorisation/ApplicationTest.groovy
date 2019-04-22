import de.youthclubstage.backend.central.authorisation.Application
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes = Application.class)
class ApplicationTest extends Specification {

    def "Application starting"() {
        given:

        when:


        expect:
        1 == 1
    }

}