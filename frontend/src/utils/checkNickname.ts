export async function checkNickname(nickname: string): Promise<boolean> {
  const res = await fetch(
    `http://3.38.153.246:8080/api/users/nickname/check?nickname=${encodeURIComponent(nickname)}`
  );
  if (!res.ok) {
    throw new Error('닉네임 중복 확인 API 호출 실패');
  }
  const data = await res.json();

  if (!data.exists) {
    console.log('중복이 아닙니다');
  } else {
    console.log('이미 사용 중인 닉네임입니다');
  }
  return data.exists;
}
