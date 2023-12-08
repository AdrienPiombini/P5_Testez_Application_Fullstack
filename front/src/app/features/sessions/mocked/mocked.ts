import { Teacher } from "src/app/interfaces/teacher.interface";
import { Session } from "../interfaces/session.interface";
import { TeacherService } from "src/app/services/teacher.service";
import { of } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";
import { SessionApiService } from "../services/session-api.service";

  export const matSnackBarMock = {
    open: jest.fn()
  } as unknown as jest.Mocked<MatSnackBar>;

  export const routerMock = {
    navigate: jest.fn(),
    url: jest.fn()
  } as unknown as jest.Mocked<Router>;

  export const activatedRouteMock = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('1')
      }
    }
  } as unknown as jest.Mocked<ActivatedRoute>;

  export const sessionMock = {
    id: 1,
    title: 'title',
    description: 'description',
    teacher_id: 1,
    start: new Date(),
    end: new Date(),
    createdAt: new Date(),
    updatedAt: new Date(),
    users: [1]
  } as unknown as jest.Mocked<Session>;

  export const sessionApiServiceMock = {
    detail: jest.fn().mockReturnValue(of(sessionMock)),
    participate: jest.fn().mockReturnValue(of(sessionMock)),
    unParticipate: jest.fn().mockReturnValue(of(sessionMock)),
    delete: jest.fn().mockReturnValue(of(sessionMock))
  } as unknown as jest.Mocked<SessionApiService>;

  export const teacherMock: Teacher = {
    id: 1, lastName: 'Doe',
    firstName: 'John',
    createdAt: new Date(),
    updatedAt: new Date()
  };

  export const teacherServiceMock = {
    detail: jest.fn().mockReturnValue(of(teacherMock))
  } as unknown as jest.Mocked<TeacherService>;


  export const sessionServiceMock = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }