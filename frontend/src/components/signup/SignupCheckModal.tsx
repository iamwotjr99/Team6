'use client';

import { useRouter } from 'next/navigation';
import Button from '../common/Button';
import { useModalStore } from '@/stores/Modal';
import { useNicknameStore } from '@/stores/Nickname';
import { useAuthStore } from '@/stores/Auth';
import { signupUser } from '@/app/api/auth';

export default function SignupCheckModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();
  const nickname = useNicknameStore.use.nickname();
  const kakaoEmail = useAuthStore.use.kakaoEmail();
  const setAccessToken = useAuthStore.use.setAccessToken();
  const kakaoId = useAuthStore.use.kakaoId();

  const handleClose = () => {
    setModal(null);
  };

  const handleSignupAndOpenMain = async () => {
    try {
      console.log('nickname:', nickname);
      console.log('kakaoEmail:', kakaoEmail);
      console.log(kakaoId);
      if (!nickname || !kakaoEmail || !kakaoId) {
        alert('닉네임 또는 이메일 정보가 없습니다.');

        return;
      }

      await signupUser(nickname, kakaoId, kakaoEmail);
      setModal(null);
      router.push('/chat');
    } catch (error) {
      console.error('[회원가입 에러]:', error);
      alert('회원가입 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div>
        <div className="flex text-base font-semibold leading-[29px] text-[#171717]">
          <p>
            <span className="font-mono font-bold italic">[{nickname}]</span>으로 만드시겠습니까?
          </p>
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="border bg-white text-black h-12"
          onClick={handleSignupAndOpenMain}
        >
          네
        </Button>
        <Button className="h-12" onClick={handleClose}>
          아니요
        </Button>
      </div>
    </div>
  );
}