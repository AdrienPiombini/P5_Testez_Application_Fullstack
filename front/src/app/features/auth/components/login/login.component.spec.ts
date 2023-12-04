import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { error } from 'cypress/types/jquery';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let router: Router;
  let sessionService: SessionService;
  let authService: AuthService;
  const mockSessionInformation: SessionInformation = {
    token: 'jwt',
    type: '',
    id: 1,
    username: 'fooBar',
    firstName: 'foo',
    lastName: 'bar',
    admin: true,
  };

  router = {
    navigate: jest.fn(),
  } as unknown as jest.Mocked<Router>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        AuthService,
        { provide: Router, useValue: router },
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to sessions ', () => {
   component.form.setValue({ email: 'test@mail.com', password: '1234' });

   const authServiceSpy = jest
    .spyOn(authService, 'login')
    .mockReturnValue(of(mockSessionInformation));
   const sessionServiceSpy = jest.spyOn(sessionService, 'logIn');
   const routerSpy = jest.spyOn(router, 'navigate');

   component.submit();

   expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
   expect(sessionServiceSpy).toHaveBeenCalled();
   expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  });

  // it('should throw an error', () => {
  //   component.form.setValue({ email: 'test@mail.com', password: '1234' });

  //  const authServiceSpy = jest
  //   .spyOn(authService, 'login');

  //   component.submit();

  //   expect(component.onError).toBe(true);
  // })
});
