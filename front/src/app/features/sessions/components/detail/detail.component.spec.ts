import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { SessionApiService } from '../../services/session-api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TeacherService } from 'src/app/services/teacher.service';
import { sessionApiServiceMock, teacherServiceMock, matSnackBarMock, routerMock, activatedRouteMock, sessionMock, sessionServiceMock } from '../../mocked/mocked';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  const userIdMocked  = sessionMock.users.toString()

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatIconModule,
        MatCardModule,
      ],
      declarations: [DetailComponent], 
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: SessionApiService, useValue: sessionApiServiceMock },
        { provide: TeacherService, useValue: teacherServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ],
    })
      .compileComponents();
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('fetch a session on init', () => {
    component.participate();
    component.unParticipate();

    expect(sessionApiServiceMock.unParticipate).toHaveBeenCalledWith(userIdMocked,userIdMocked);
    expect(sessionApiServiceMock.participate).toHaveBeenCalledWith(userIdMocked, userIdMocked);
    expect(sessionApiServiceMock.detail).toHaveBeenCalledWith(userIdMocked);
    expect(teacherServiceMock.detail).toHaveBeenCalledWith(userIdMocked);
  });


  it('navigate too sessions when call delete a session', () => {
    component.delete();

    expect(sessionApiServiceMock.delete).toHaveBeenCalledWith(userIdMocked);
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(routerMock.navigate).toBeCalledWith(['sessions']);
  })

  it('should navigate back', () => {
    const backSpy = jest.spyOn(window.history, 'back');

    component.back();

    expect(backSpy).toHaveBeenCalled();
    backSpy.mockRestore();
  });


});

