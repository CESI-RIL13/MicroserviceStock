package microservicestock

import grails.rest.*

@Resource(formats=['html','json', 'xml'])
class Fiche {

	Date date_creation
    String designation
    String conditionnement
    String unite_gestion
    Float poids
    Float volume
    String niveau_danger
    String mode_valorisation
    String classe
    Float prix_achat
    Float prix_vente
    Integer en_stock
    Integer en_commande
    Date date_reapro
    Integer quantite_reapro
    Integer delai_reapro
    Integer taille_lot
    Float cout_stockage

    static hasOne = [famille:Famille,fabricant:Fabricant]
    static hasMany = [produits:Produit]

    static constraints = {
        mode_valorisation nullable: true
        date_reapro nullable: true
    }
}
