import Button from '@/components/common/Button';
import { IoChevronForward } from 'react-icons/io5';

export default function  Setting() {
  return (
    <div>
      <div className="border-b px-4 pt-3 pb-1">
        <span className="text-lg font-mono font-bold">유저 서비스</span>
      </div>
      <div className='flex items-center justify-between px-4 py-4'>
        <span className='pt-1 text-lg '>다크모드 설정</span>
          <Button className='w-7 h-10 bg-white'>
          <IoChevronForward size={30} color='black'/>
          </Button>

      </div>
      <div className="flex items-center justify-between px-4 py-4">
        <span className="items-center pt-1 text-lg">
          개인정보처리방침
        </span>
        <Button className='w-7 h-10 bg-white'>
          <IoChevronForward size={30} color='black'/>
          </Button>
      </div>
      <div className="flex items-center justify-between px-4 py-4">
        <span className="items-center pt-1 text-lg">
          이용약관
        </span>
        <Button className='w-7 h-10 bg-white'>
          <IoChevronForward size={30} color='black'/>
          </Button>
      </div>
      <div className="flex items-center justify-between px-4 py-4">
        <span className="text-lg">고객 문의</span>
        <p className="text-lg">email@email.com</p>
      </div>
      <p className="w-96 px-4 py-4 text-base">
        문의 사항이 있으신 경우 이메일을 보내주시면 신속하게 확인하고 답변해
        드리겠습니다.
      </p>
      <div className='h-2 w-full' />
      <div className='flex justify-between px-4 pt-2 pb-8 border-t'>
        <div/>
        <Button
          className='bg-white text-base text-red-400 font-bold w-20 h-7 py-2'
        >
          회원탈퇴
        </Button>
      </div>
    </div>
  )
}