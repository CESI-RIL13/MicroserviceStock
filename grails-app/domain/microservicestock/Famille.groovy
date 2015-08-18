package microservicestock
import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Famille {
    String nom

    static hasOne = [categorie:Categorie]
    static hasMany = [familles:Famille,fiches:Fiche]
    static belongsTo = [Famille]

    static constraints = {
        familles nullable: true
        fiches nullable: true
    }
}
