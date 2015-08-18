class UrlMappings {

    static mappings = {


        "/fiches"(resources:"fiche") {
            "/fabricant"(resource:"fabricant")
            "/famille"(resource:"famille")
            "/produits"(resources:"produit")
        }
        "/fabricants"(resources:"fabricant") {
            "/fiches"(resources:"fiche")
        }
        "/familles"(resources:"famille") {
            "/categorie"(resource:"categorie")
            "/famille"(resource:"famille")
            "/sousFamilles"(resources:"famille")
            "/fiches"(resources:"fiche")
        }
        "/categories"(resources:"categorie") {
            "/familles"(resources:"famille")
        }
        "/produits"(resources:"produit") {
            "/fiche"(resource:"fiche")
            "/accidents"(resources:"accident")
        }
        "/accidents"(resources:"accident") {
            "/produit"(resource: "produit")
        }
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
