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
import { empty, of } from 'rxjs';
import { Router } from '@angular/router';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
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

  it('should naigate to login route', () => { 
    const formValue = {
      email : 'foo@bar.com',
      firstName:'foo',
      lastName: 'bar',
      password:'secret'
    };

    const authService = TestBed.inject(AuthService);
    const authServiceSpy = jest.spyOn(authService, 'register').mockReturnValue(of(void 0))


    let router = TestBed.inject(Router);
    router = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;
    const routerSpy = jest.spyOn(router, 'navigate');
    component.form.setValue(formValue);

    //omponent.submit();

    //expect(authServiceSpy).toHaveBeenCalledWith(formValue);

    // expect(routerSpy.na).toHaveBeenCalledWith(['/login']);


    
  })
});
