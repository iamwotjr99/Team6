export async function checkNickname(nickname: string): Promise<boolean> {
  const res = await fetch('');
  const data = await res.json();
  return data.exists;
}
