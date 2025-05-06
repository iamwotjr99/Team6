'use client';
import Button from '@/components/common/Button';
import { IoChevronForward } from 'react-icons/io5';
import { useModalStore } from '@/stores/Modal';
import WithdrawModal from './WithdrawModal';
import { useRouter } from 'next/navigation';

export default function Setting() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();

  const handleWithdrawModal = () => {
    setModal(<WithdrawModal />);
  };

  const handlePrivacyPolicy = () => {
    router.push('/privacy');
  };

  const handleTermsOfService = () => {
    router.push('/term');
  };
  return (
    <div className='min-h-screen bg-white text-black transition-colors duration-500'>
      <div className='border-b border-gray-300  px-4 pt-3 pb-1'>
        <span className='text-lg font-mono font-bold'>유저 서비스</span>
      </div>
      <div className='flex items-center justify-between px-4 py-4'>
        <span className='items-center pt-1 text-lg'>개인정보처리방침</span>
        <Button
          className='w-7 h-10 bg-white'
          onClick={handlePrivacyPolicy}
        >
          <IoChevronForward size={30} color='black' />
        </Button>
      </div>

      <div className='flex items-center justify-between px-4 py-4'>
        <span className='items-center pt-1 text-lg'>이용약관</span>
        <Button
          className='w-7 h-10 bg-white'
          onClick={handleTermsOfService}
        >
          <IoChevronForward size={30} color='black' />
        </Button>
      </div>

      <div className='flex items-center justify-between px-4 py-4'>
        <span className='text-lg'>고객 문의</span>
        <p className='text-lg'>email@email.com</p>
      </div>

      <p className='w-96 px-4 py-4 text-base'>
        문의 사항이 있으신 경우 이메일을 보내주시면 신속하게 확인하고 답변해
        드리겠습니다.
      </p>

      <div className='h-2 w-full' />
      <div className='flex justify-between px-4 pt-2 pb-8 border-t border-gray-300'>
        <div />
        <Button
          onClick={handleWithdrawModal}
          className='bg-white text-base text-red-400 font-bold w-20 h-7 py-4'
        >
          회원탈퇴
        </Button>
      </div>
    </div>
  );
}
