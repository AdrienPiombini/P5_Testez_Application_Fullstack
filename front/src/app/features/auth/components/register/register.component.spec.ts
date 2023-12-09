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
import {  of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { authServiceMock, registerRequest, routerMock } from '../../mocked/mocked';



describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers:[
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
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
  authServiceMock.register.mockReturnValue(of(void 0));
  component.form.setValue(registerRequest);

  component.submit();

  expect(authServiceMock.register).toHaveBeenCalledWith(registerRequest);
  expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);

  })

  it('should throw', () => { 
    authServiceMock.register.mockReturnValue(throwError(() => new Error()))
    component.form.setValue(registerRequest);
  
    component.submit();
    
  expect(component.onError).toBe(true);
    })
});
