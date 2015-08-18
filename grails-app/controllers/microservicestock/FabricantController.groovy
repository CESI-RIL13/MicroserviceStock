package microservicestock

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FabricantController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fabricant.list(params), model:[fabricantCount: Fabricant.count()]
    }

    def show(Fabricant fabricant) {
        respond fabricant
    }

    def create() {
        respond new Fabricant(params)
    }

    @Transactional
    def save(Fabricant fabricant) {
        if (fabricant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fabricant.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fabricant.errors, view:'create'
            return
        }

        fabricant.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fabricant.label', default: 'Fabricant'), fabricant.id])
                redirect fabricant
            }
            '*' { respond fabricant, [status: CREATED] }
        }
    }

    def edit(Fabricant fabricant) {
        respond fabricant
    }

    @Transactional
    def update(Fabricant fabricant) {
        if (fabricant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fabricant.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fabricant.errors, view:'edit'
            return
        }

        fabricant.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fabricant.label', default: 'Fabricant'), fabricant.id])
                redirect fabricant
            }
            '*'{ respond fabricant, [status: OK] }
        }
    }

    @Transactional
    def delete(Fabricant fabricant) {

        if (fabricant == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        fabricant.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'fabricant.label', default: 'Fabricant'), fabricant.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fabricant.label', default: 'Fabricant'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
