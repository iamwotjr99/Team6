'use client';

import { useAuthStore } from '@/stores/Auth';

export async function loginWithKakao(kakaoEmail: string) {
  const res = await fetch('http://3.38.153.246:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify({ kakaoEmail }),
  });
  if (!res.ok) {
    throw new Error('로그인 요청 실패');
  }
  const json = await res.json();
  return json.data; 
}

export async function signupUser(nickname: string, kakaoId: number, kakaoEmail: string) {
  console.log(nickname);
  const res = await fetch('http://3.38.153.246:8080/api/auth/signup', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify({ nickname, kakaoId, kakaoEmail }),
  });

  if (!res.ok) {
    throw new Error('회원가입 실패');
  }

  const json = await res.json();
  return json.data;
}

export async function refreshAccessToken() {
  const res = await fetch('http://3.38.153.246:8080/api/auth/refresh', {
    method: 'POST',
    credentials: 'include',
  });

  if (!res.ok) {
    throw new Error('Access Token 재발급 실패');
  }

  const json = await res.json();
  console.log('[refreshAccessToken 성공]', json);
  return json.data.accessToken;
}

export async function logoutUser() {
  const accessToken = useAuthStore.getState().accessToken;
  const clearAuth = useAuthStore.getState().clearAuth;

  const res = await fetch('http://3.38.153.246:8080/api/auth/logout', {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    credentials: 'include',
  });

  if (!res.ok) {
    throw new Error('로그아웃 실패');
  }
  
  clearAuth();
  if (useAuthStore.persist?.clearStorage) {
    await useAuthStore.persist.clearStorage();
  }
}

export async function fetchMyInfo() {
  const accessToken = useAuthStore.getState().accessToken;

  if (!accessToken) {
    throw new Error('AccessToken이 없습니다.');
  }

  const res = await fetch('http://3.38.153.246:8080/api/users/my', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`, 
    },
    credentials: 'include',
  });

  if (!res.ok) {
    throw new Error('내 정보 가져오기 실패');
  }

  const json = await res.json();
  return json.data; 
}