import { useRouter } from 'next/navigation';
import Button from '../common/Button';
import { useModalStore } from '@/stores/Modal';

export default function SignupCheckModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();

  const handleClose = () => {
    setModal(null);
    router.push('/signup');
  };

  // 회원가입 완료하면 메인화면으로 
  const handleOpenMain = () => {
    setModal(null);
    router.push('/chat');
  }

  return (
    <div className='flex flex-col gap-5 px-4 py-6 items-center justify-center'>
      <div>
        <div className='flex text-base font-semibold leading-[29px] text-[#171717]'>
          <p><span className='font-mono font-bold italic'>[닉네임]</span>으로 만드시겠습니까?</p>
        </div>
      </div>
      <div className='flex gap-4 font-bold w-60 items-center'>
        <Button
          className='border bg-white text-black h-12'
          onClick={handleOpenMain}
        >
          네
        </Button>
        <Button className='h-12' onClick={handleClose}>
          아니요
        </Button>
      </div>
    </div>
  );
}
