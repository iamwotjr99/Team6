import MyInfo from '@/components/mypage/MyInfo';
import SelectHeader from '@/components/layout/header/SelectHeader';
import Setting from '@/components/mypage/Setting';

export default function Page() {

  const handleLogout= () => {

  }

  return (
    <section className="h-full overflow-y-auto">
      <SelectHeader mainText='마이 페이지' buttonText='Logout'/>
      <MyInfo />
      <Setting />
    </section>
  )
}