'use client';

import Button from '@/components/common/Button';
import { useRouter } from 'next/navigation';
import { useModalStore } from '@/stores/Modal';
import { useAuthStore } from '@/stores/Auth';
// import { withdrawUser } from '@/app/api/auth'; 

export default function WithdrawModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();
  const clearAuth = useAuthStore.use.clearAuth(); 

  const handleClose = () => {
    setModal(null);
    router.push('/mypage');
  };

  const withDraw = async () => {
    try {
      // await withdrawUser(); 
      clearAuth(); 
      setModal(null);
      router.push('/'); 
    } catch (error) {
      console.error('[회원탈퇴 에러]:', error);
      alert('회원탈퇴 중 문제가 발생했습니다.');
    }
  };

  return (
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div>
        <div className="flex text-lg font-bold leading-[29px] text-[#171717]">
          <p>회원탈퇴 하시겠습니까?</p>
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="bg-white border text-black h-12"
          onClick={withDraw}
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