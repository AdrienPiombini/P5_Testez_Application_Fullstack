// /// <reference types="Cypress" />

// describe('template spec', () => {

//   it('Register a user', () => {
//     cy.visit('/register')

//     cy.intercept('POST', 'api/auth/register',{})

//     cy.get('input[formControlName=firstName]').type("foo")
//     cy.get('input[formControlName=lastName]').type("bar")
//     cy.get('input[formControlName=email]').type("foo@bar.com")
//     cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

//     cy.url().should('include', '/login');
//   })

//   it('should throw a erro', () => {
//     cy.visit('/register')

//     cy.intercept('POST', 'api/auth/register')

//     cy.get('input[formControlName=firstName]').type("foo")
//     cy.get('input[formControlName=lastName]').type("bar")
//     cy.get('input[formControlName=email]').type("foo@bar.com")
//     cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)


//     cy.url().should('include', '/register');
//   })
  
// })

