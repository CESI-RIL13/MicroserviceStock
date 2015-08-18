package microservicestock

import grails.test.mixin.*
import spock.lang.*

@TestFor(FabricantController)
@Mock(Fabricant)
class FabricantControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.fabricantList
            model.fabricantCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.fabricant!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def fabricant = new Fabricant()
            fabricant.validate()
            controller.save(fabricant)

        then:"The create view is rendered again with the correct model"
            model.fabricant!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            fabricant = new Fabricant(params)

            controller.save(fabricant)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/fabricant/show/1'
            controller.flash.message != null
            Fabricant.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def fabricant = new Fabricant(params)
            controller.show(fabricant)

        then:"A model is populated containing the domain instance"
            model.fabricant == fabricant
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def fabricant = new Fabricant(params)
            controller.edit(fabricant)

        then:"A model is populated containing the domain instance"
            model.fabricant == fabricant
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/fabricant/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def fabricant = new Fabricant()
            fabricant.validate()
            controller.update(fabricant)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.fabricant == fabricant

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            fabricant = new Fabricant(params).save(flush: true)
            controller.update(fabricant)

        then:"A redirect is issued to the show action"
            fabricant != null
            response.redirectedUrl == "/fabricant/show/$fabricant.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/fabricant/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def fabricant = new Fabricant(params).save(flush: true)

        then:"It exists"
            Fabricant.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(fabricant)

        then:"The instance is deleted"
            Fabricant.count() == 0
            response.redirectedUrl == '/fabricant/index'
            flash.message != null
    }
}
