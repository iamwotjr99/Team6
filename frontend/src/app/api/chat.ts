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
    const errorText = await res.text();
    console.error('[createChat 실패]:', errorText);
    throw new Error('채팅방 생성 실패');
  }
  const json = await res.json();
  return json.data; 
}