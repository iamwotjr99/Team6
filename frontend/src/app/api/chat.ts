import { useAuthStore } from '@/stores/Auth';

export async function createChat(title: string) {
  const accessToken = useAuthStore.getState().accessToken;
  const res = await fetch('http://3.38.153.246:8080/api/chatroom', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`,
    },
    credentials: 'include',
    body: JSON.stringify({ title }),
  });
  if (!res.ok) {
    throw new Error('채팅방 생성 실패');
  }
  const json = await res.json();
  return json.data; 
}


export async function fetchMyChat() {
  const accessToken = useAuthStore.getState().accessToken;

  const res = await fetch('http://3.38.153.246:8080/api/chatroom/joined', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`, 
    },
    credentials: 'include',
  });

  if (!res.ok) {
    throw new Error('내 채팅 가져오기 실패');
  }

  const json = await res.json();
  return json.data; 
}

export async function fetchWeChat() {
  const accessToken = useAuthStore.getState().accessToken;

  const res = await fetch('http://3.38.153.246:8080/api/chatroom/not-joined', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`, 
    },
    credentials: 'include',
  });

  if (!res.ok) {
    throw new Error('모든 채팅 가져오기 실패');
  }

  const json = await res.json();
  return json.data; 
}