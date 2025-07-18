package com.jyanie.nietzsche.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final List<String> quotes = List.of(
            "나를 죽이지 못하는 것은 나를 더 강하게 만든다.",
            "나는 언젠가 사람들이 나를 신성시할까 봐 두렵기 그지없다. 이제 사람들은 내가 왜 이 책을 먼저 출간하는지를 알아차릴 수 있을 것이다. 나는 사람들이 나에 대해 헛소리를 하는 것을 막고 싶은 것이다. ⋯ 나는 성자가 되고 싶지 않다. 차라리 어릿광대가 되고 싶다.",
            "춤추는 별을 낳기 위해선 내면에 혼돈을 지녀야 한다.",
            "괴물과 싸우는 사람은 그 과정에서 괴물이 되지 않도록 조심해야 한다. 네가 오랫동안 심연을 들여다보고 있으면, 심연 또한 네 속을 들여다볼 것이므로.",
            "신은 죽었다. 신은 죽어 있다. 그리고 우리가 그를 죽여버렸다. 살인자 중의 살인자인 우리는 어떻게 스스로를 위로할 것인가?",
            "음악이 없다면 인생은 잘못된 것이다.",
            "모든 글 중에서 나는 누군가가 피로 쓴 것만을 사랑한다. 피로 써라. 그러면 그대는 피가 곧 정신임을 알게 될 것이다.",
            "인간은 극복되어야 할 그 무엇이다.",
            "위험하게 살아라! 베수비오 화산의 비탈에 너의 도시를 세워라!",
            "삶의 여로를 걷는 우리들은 여행자다. 가장 비참한 여행자는 누군가를 따라가는 인간이며, 가장 위대한 여행자는 습득한 모든 지혜를 남김없이 발휘하여 스스로 목적지를 선택하는 인간이다.",
            "하루의 3분의 2를 자신을 위해 쓰지 못하는 사람은 노예다.",
            "젊은이를 망치는 가장 확실한 방법은 '자신과 다른 생각을 하는 사람' 대신에 '자신과 같은 생각을 하는 사람'을 존경하도록 가르치는 것이다.",
            "깊이있는 모든 사상가들은 오해받는 것보다 이해받는 것을 더 두려워한다.",
            "그대의 몸은 그대의 철학보다 더 많은 지혜를 품고 있다.",
            "더러운 것에 대한 혐오가 지나치면, 스스로를 정화시키고자 하거나 정당화하는 데에 장애가 될 수 있다.",
            "진리는 추악하다. 진리에 의해서 멸하지 않기 위해 우리는 예술을 가지는 것이다.",
            "옛사람들이 '신을 위해서' 행했던 것을 요즘 사람들은 돈을 위해서 행한다.",
            "나는 욕구가 소박한 예술가를 사랑한다. 그가 진정으로 원하는 것은 단 두 가지, 자신이 먹을 빵과 자신의 예술이다.",
            "우리는 정말 우리 삶의 시인이 되고자 한다. 무엇보다도 가장 사소하고 가장 평범한 일에서부터.",
            "결혼 생활을 불행하게 만드는 것은 사랑의 결핍이 아니라 우정의 결핍이다.",
            "아이들이냐 아니면 책이냐.",
            "인간이 궁극적으로 사랑하는 것은 자신의 욕망이지 그 욕망의 대상은 아니다.",
            "친구를 최고의 적으로 삼아야 한다. 친구에게 반대할 때, 너는 마음속으로 그를 더없이 가깝게 여겨야 한다.",
            "살아야 할 이유를 가지고 있는 사람은 그 어떤 방식으로든 견뎌낼 수 있다.",
            "오늘 가장 잘 웃는 자가 최후에도 웃을 것이다.",
            "나쁜 기억력의 장점은 동일한 좋은 것을 여러 번이나 마치 처음처럼 즐길 수 있다는 점이다.",
            "인간의 성숙 : 어렸을 때 놀이에서 가졌던 진지함을 다시 발견하는 것.",
            "네 운명을 사랑하라 (Amor fati): 이것이 지금부터 나의 사랑이 될 것이다! 나는 추한 것과 전쟁을 벌이지 않으련다. 나는 비난하지 않으련다. 나를 비난하는 자도 비난하지 않으련다. 눈길을 돌리는 것이 나의 유일한 부정이 될 것이다! 무엇보다 나는 언젠가 긍정하는 자가 될 것이다!",
            "이것이 삶이었나? 좋다! 그렇다면 다시 한번!",
            "모두 그리고 각자에게 하는 질문: '너는 이 삶을 다시 한 번, 그리고 무수히 반복하길 원하는가?' 는 마치 최대의 중량으로 그대의 행동 위에 얹힐 것이다!",
            "내가 지금 거짓말을 한다면 나는 더 이상 성실한 사람이 아니며 누구나 내 면전에서 그렇게 말해도 된다.",
            "너는 너 자신이 되어야 한다.",
            "나의 제자들이여, 나는 이제 홀로 가련다! 그대들도 이제 홀로 떠나라! 그것이 내가 바라는 것이다. 나를 떠나서 차라투스트라에 저항하라! 아니 차라리 그를 부끄러워하라! 그가 그대를 속였을 수도 있으니... 언제나 제자인 채로 머문다면, 그대들은 스승의 은혜를 저버리는 것이다. 그대들은 어찌하여 나의 월계관을 빼앗으려 하지 않는가?... 이제 나는 그대들에게 명한다. 나를 버리고 그대들 자신을 찾아라. 그대들이 모두 나를 부인하고 나서야 비로소 나는 그대들에게 돌아오리라."
    );

    // 오늘의 명언
    @GetMapping("/today")
    public String getTodaysQuotes() {
        int dayOfYear = LocalDate.now().getDayOfYear(); // 1월 1일이면 1, 6월 16일이면 168, 365일 중 몇 일째인지.
        int index = dayOfYear % quotes.size(); // quotes.size() == 33이면 인덱스는 0~32만 가능
        return quotes.get(index);
    }

    // 전체 명언 목록
    @GetMapping
    public List<String> getAllQuotes() {
        return quotes;
    }

}
