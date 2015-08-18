package microservicestock

import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Categorie {
    String name

    static hasMany = [familles:Famille]
    static constraints = {
        familles nullable: true
    }
}
