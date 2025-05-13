import { useNicknameStore } from '@/stores/Nickname';

export function useNickname() {
  const nickname = useNicknameStore.use.nickname();
  const setNickname = useNicknameStore.use.setNickname();
  const generateNickname = useNicknameStore.use.generateNickname();

  return {
    nickname,
    setNickname,
    generateNickname,
  };
}