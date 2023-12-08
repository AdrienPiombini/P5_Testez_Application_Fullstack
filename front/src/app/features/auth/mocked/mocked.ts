import { Teacher } from 'src/app/interfaces/teacher.interface';
import { TeacherService } from 'src/app/services/teacher.service';
import { of } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Session } from '../../sessions/interfaces/session.interface';
import { SessionApiService } from '../../sessions/services/session-api.service';
import { AuthService } from '../services/auth.service';
import { TestBed } from '@angular/core/testing';



export const authServiceMock = {
  register: jest.fn(),
  login: jest.fn(),
} as unknown as jest.Mocked<AuthService>;

export const sessionServiceMock = {
  logIn: jest.fn(),
};

export const routerMock = {
  navigate: jest.fn(),
} as unknown as jest.Mocked<Router>;

export const registerRequest = {
  email: 'foo@bar.com',
  firstName: 'foo',
  lastName: 'bar',
  password: 'secret',
};

export const sessionInformationMock = {
  token: 'jwt',
  type: '',
  id: 1,
  username: 'fooBar',
  firstName: 'foo',
  lastName: 'bar',
  admin: true,
};

export const formValue = { email: 'test@mail.com', password: '1234' };
