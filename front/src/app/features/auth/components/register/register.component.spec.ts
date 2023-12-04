import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { empty, of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: jest.Mocked<AuthService>;
  let router: jest.Mocked<Router>;

  const registerRequest = {
    email : 'foo@bar.com',
    firstName:'foo',
    lastName: 'bar',
    password:'secret'
  };

  beforeEach(async () => {
    authService = {
      register: jest.fn(),
      login: jest.fn(),
    } as unknown as jest.Mocked<AuthService>;
    router = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers:[
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router },
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to login route', () => { 
  authService.register.mockReturnValue(of(void 0))
  component.form.setValue(registerRequest);

  component.submit();

  expect(authService.register).toHaveBeenCalledWith(registerRequest);
  expect(router.navigate).toHaveBeenCalledWith(['/login']);

  })

  it('should throw', () => { 
    authService.register.mockReturnValue(throwError('error'))
    component.form.setValue(registerRequest);
  
    component.submit();
    
  expect(component.onError).toBe(true);
    })
});
