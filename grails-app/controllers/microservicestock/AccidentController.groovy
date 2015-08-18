package microservicestock

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AccidentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Accident.list(params), model:[accidentCount: Accident.count()]
    }

    def show(Accident accident) {
        respond accident
    }

    def create() {
        respond new Accident(params)
    }

    @Transactional
    def save(Accident accident) {
        if (accident == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accident.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accident.errors, view:'create'
            return
        }

        accident.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accident.label', default: 'Accident'), accident.id])
                redirect accident
            }
            '*' { respond accident, [status: CREATED] }
        }
    }

    def edit(Accident accident) {
        respond accident
    }

    @Transactional
    def update(Accident accident) {
        if (accident == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accident.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accident.errors, view:'edit'
            return
        }

        accident.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accident.label', default: 'Accident'), accident.id])
                redirect accident
            }
            '*'{ respond accident, [status: OK] }
        }
    }

    @Transactional
    def delete(Accident accident) {

        if (accident == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accident.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accident.label', default: 'Accident'), accident.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accident.label', default: 'Accident'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
