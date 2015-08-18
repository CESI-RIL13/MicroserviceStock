package microservicestock
import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Produit {
    
    Long emplacement
    Long code_barre
    Date date_entree
    Date date_sortie
    Long ref_bon_reception
    Long ref_bon_expedition

    static hasOne = [fiche:Fiche]
    static hasMany = [accidents:Accident]
    static constraints = {
        date_sortie nullable: true
        ref_bon_expedition nullable: true
        accidents nullable: true
    }
}
