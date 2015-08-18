package microservicestock
import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Fabricant {

    String name

    static hasMany = [fiches:Fiche]

    static constraints = {
    }
}
