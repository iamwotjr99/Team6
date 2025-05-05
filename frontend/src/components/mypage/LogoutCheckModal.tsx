'use client';

import Button from '@/components/common/Button';
import { useRouter } from 'next/navigation';
import { useModalStore } from '@/stores/Modal';
import { useAuthStore } from '@/stores/Auth';
import { logoutUser } from '@/app/api/auth';

export default function LogoutCheckModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();
  const clearAuth = useAuthStore.use.clearAuth();
  const accessToken = useAuthStore.use.accessToken();

  const handleClose = () => {
    setModal(null);
  };

  const logout = async () => {
    try {
      await logoutUser(); 
      setModal(null);
      router.push('/');
    } catch (error) {
      console.error('[Logout 에러]:', error);
      alert('로그아웃 중 문제가 발생했습니다.');
    }
  };

  return (
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div>
        <div className="flex text-lg font-bold leading-[29px] text-[#171717]">
          <p>로그아웃 하시겠습니까?</p>
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="bg-white border text-black h-12"
          onClick={logout}
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