/// <reference types="Cypress" />
describe('Login Component', () => {
  beforeEach(()=>{
    cy.visit('/login')
  })
  it('Login and logout successfull', () => {

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get('.link').contains('Logout').click();
    
    cy.url().should('equal', 'http://localhost:4200/')
  })

  it('return an error when form values not valid', () => {
    cy.get('input[formControlName=email]').type("fake@email.com")
    cy.get('input[formControlName=password]').type(`${"secret1234"}{enter}{enter}`)
    cy.get('.error').should('be.visible');
  })

  it('return an error when password not valid', () => {
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"secret1234"}{enter}{enter}`)
    cy.get('.error').should('be.visible');
  })
});

describe('Register Component', () => {
beforeEach(()=>{
  cy.visit('/register')
})
  it('Register a user', () => {
    cy.intercept('POST', 'api/auth/register',{
      statusCode:201
    })

    cy.get('input[formControlName=firstName]').type("foo")
    cy.get('input[formControlName=lastName]').type("bar")
    cy.get('input[formControlName=email]').type("foo@bar.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/login');
  })

  it('should throw a error', () => {
    cy.intercept('POST', 'api/auth/register', {
      statusCode:403
    })

    cy.get('input[formControlName=firstName]').type("foo")
    cy.get('input[formControlName=lastName]').type("bar")
    cy.get('input[formControlName=email]').type("foo@bar.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/register');
    cy.get('.error').should('be.visible');
  })

  it('should disable submit button if missing form value', () => {
    cy.visit('/register')
    cy.get('input[formControlName=email]').should('have.class', 'ng-invalid');
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should disable submit button if missing email not correct', () => {
    cy.get('input[formControlName=firstName]').type("foo")
    cy.get('input[formControlName=lastName]').type("bar")
    cy.get('input[formControlName=email]').type("foobar.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('input[formControlName=email]').should('have.class', 'ng-invalid');
    cy.get('button[type=submit]').should('be.disabled');
  });
})

describe('Account Component', ()=> {
  beforeEach(()=>{
    cy.visit('/login')
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        email: 'foo@bar.com',
        lastName: 'Foo',
        firstName: 'Bar',
        admin: false,
        createdAt: '2023-12-12',
        updatedAt: '2023-12-12',
      },
    });

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        email: 'foo@bar.com',
        lastName: 'Foo',
        firstName: 'Bar',
        admin: false,
        createdAt: '2023-12-12',
        updatedAt: '2023-12-14',
      }
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session');
    cy.get('input[formControlName=email]').type("foo@bar.com");
    cy.get('input[formControlName=password]').type(`${"secret12345!!"}{enter}{enter}`);
    cy.url().should('include', '/sessions');
  })

  it('should see the user informations and come back to account page', () => {
    cy.contains('span.link', 'Account').click();
    cy.url().should('equal', 'http://localhost:4200/me');
    cy.get('p:contains("Name:")').should('contain', 'Bar FOO');
    cy.get('p:contains("Email:")').should('contain', 'foo@bar.com');
    cy.get('p:contains("Create at:")').should('contain','December 12, 2023')
    cy.get('p:contains("Last update:")').should('contain','December 14, 2023')

    cy.get('.my2 > .mat-focus-indicator').should('exist');
    cy.url().should('include', '/me');
    cy.get('button.mat-icon-button').click();
    cy.url().should('not.include', '/me');
    cy.url().should('equal', 'http://localhost:4200/sessions');
  });

  it('should delete the account', () => {

    cy.intercept('DELETE', '/api/user/1', {
        statusCode: 204
    }).as('delete user');

    cy.contains('span.link', 'Account').click();
    cy.get('.my2 > .mat-focus-indicator').click();
    cy.get('.mat-simple-snack-bar-content').should('exist');
    cy.url().should('equal', 'http://localhost:4200/');
  });
})

describe('Testing the Not-found Component', () => {
  it('go to the not found page explicitly', () => {
      cy.visit('/not-found');
      cy.url().should('include', '/404');
      cy.get('h1').should('contain', 'Page not found');
  });

  it('go to the not found page not explicitly', () => {
    cy.visit('/inexistantPage');
    cy.url().should('include', '/404');
    cy.get('h1').should('contain', 'Page not found');
});
});   

describe('Account Component', ()=> {
  beforeEach(()=>{
    cy.visit('/login')
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        email: 'foo@bar.com',
        lastName: 'Foo',
        firstName: 'Bar',
        admin: false,
        createdAt: '2023-12-12',
        updatedAt: '2023-12-12',
      },
    });

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        email: 'foo@bar.com',
        lastName: 'Foo',
        firstName: 'Bar',
        admin: false,
        createdAt: '2023-12-12',
        updatedAt: '2023-12-14',
      }
    });

    cy.intercept('GET', '/api/session', {
      body: [{
        id: 1,
        name: 'yoga',
        description: 'session 1',
        teacher_id: 2,
        users: []
      }]
    });

    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'yoga',
        description: 'session 1',
        teacher_id: 2,
        users: []
      }
    });

    cy.get('input[formControlName=email]').type("foo@bar.com");
    cy.get('input[formControlName=password]').type(`${"secret12345!!"}{enter}{enter}`);
  })

  it('should participate at a yoga session', () => {
    cy.intercept('POST', '/api/session/1/participate/1', {});
    cy.get('.mat-card-actions > :nth-child(1)').click();
    cy.url().should('include', '/sessions/detail');
    cy.get('button:contains("Participate")').should('exist');
    cy.get('button:contains("Do not participate")').should('not.exist');

    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'yoga',
        description: 'session 1',
        teacher_id: 2,
        users: [1],
        createdAt: '2023-12-25',
        updatedAt: '2023-12-30'
      }
    });

    cy.get('button:contains("Participate")').click();
    cy.get('button:contains("Do not participate")').should('exist');

  });

  it('should cancel a yoga session', () => {
    cy.intercept('POST', '/api/session/1/participate/1', {});
    cy.url().should('include', '/sessions');
    cy.get('.mat-card-actions > :nth-child(1)').click();
    cy.url().should('include', '/sessions/detail');
    cy.get('button:contains("Participate")').should('exist');
    cy.get('button:contains("Do not participate")').should('not.exist');

    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'yoga',
        description: 'relax....!',
        teacher_id: 2,
        users: [1],
        createdAt: '2023-12-25',
        updatedAt: '2023-12-30'
      }
    });
    cy.get('button:contains("Participate")').click();
    cy.get('button:contains("Do not participate")').should('exist');
    cy.intercept('DELETE', '/api/session/1/participate/1', {});
    cy.intercept('GET', '/api/session/1', {
        body: {
        id: 1,
        name: 'yoga',
        description: 'session 1',
        teacher_id: 2,
        users: [],
        createdAt: '2023-12-25',
        updatedAt: '2023-12-30'
        }
    });
    cy.get('button:contains("Do not participate")').click();
    cy.get('button:contains("Participate")').should('exist');
  });

})

describe('Form Component', () => {
  beforeEach(()=>{
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'yoga@studio.com',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    });

    cy.intercept('GET', '/api/teacher', { 
      body: [
        { id: 1, firstName: 'TeacherFirstName', lastName: 'TeacherLastName' }
      ] 
    });

    cy.intercept('POST', '/api/session', {
      body: {
        id: 1,
        name: 'Session 1',
        description: 'Yoga Session',
        date: '2023-12-15',
        teacher_id: 1,
      },
    });

    cy.intercept('GET', '/api/session', {
      body: [{
        id: 1,
        name: 'Session 1',
        description: 'Yoga Session',
        date: '2023-12-15',
        teacher_id: 1,
        users: []
      }]
    });

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(`${'test!1234'}{enter}{enter}`);
  })

  it('should create a yoga session', () => {
    cy.get('[fxlayout="row"] > .mat-focus-indicator > .mat-button-wrapper > .ml1').click()
    cy.get('input[formControlName="name"]').type('New session');
    cy.get('input[formControlName="date"]').type('2023-12-18');
    cy.get('mat-select[formControlName="teacher_id"]').click().get('mat-option').contains('TeacherFirstName').click();
    cy.get('textarea[formControlName="description"]').type('new session');
    cy.get('button[type="submit"]').click();
    cy.get('.mat-snack-bar-container').should('be.visible');
  })

  it('should delete a yoga session', ()=> {
    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Session to delete',
        description: 'Will be deleted',
        date: '2021-05-03',
        teacher_id: 1,
        users: []
      }
    });
    cy.intercept('DELETE', '/api/session/1', {
      statusCode: 204
    });
    cy.get('.mat-card-actions > :nth-child(1)').click()
    cy.get('.mat-button-wrapper > .ml1').click()
    cy.get('.mat-snack-bar-container').should('be.visible');
  });

 it('should update a session', () => {
    cy.url().should('include', '/sessions');

    cy.intercept('GET', '/api/teacher', {
      body: [{
          id: 1,
          firstName: 'TeacherFirstName',
          lastName: 'TeacherLastName',
      }]
    });
  
    cy.intercept('GET', '/api/session/1', {
      body: {
        id: 1,
        name: 'Session to update',
        description: 'Will be updated',
        date: '2021-05-03',
        teacher_id: 1,
        users: []
      }
    });
  
    cy.intercept('PUT', '/api/session/1', {
      body: {
        id: 1,
        name: 'Session updated',
        description: 'Updated',
        date: '2023-12-03',
        teacher_id: 1,
        users: []
      }
    });

    cy.get('.mat-card-actions > .ng-star-inserted').click();
    cy.get('h1').invoke('text').should('contains', 'Update session');
    cy.get('input[formControlName=name]').clear().type("Session updated");
    cy.get('input[formControlName=date]').type("2023-12-03");
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('TeacherFirstName').click();
    cy.get('textarea[formControlName=description]').clear().type(`Updated`);
    cy.get('.mt2 > [fxlayout="row"] > .mat-focus-indicator > .mat-button-wrapper').click()
    cy.url().should('include', '/sessions');
    cy.get('.mat-snack-bar-container').should('be.visible');

  });

})