package microservicestock

import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Accident {
    Date date
    Long ref_trajet
    String annotation

    static belongsTo = [produit:Produit]
    static constraints = {
    }
}
